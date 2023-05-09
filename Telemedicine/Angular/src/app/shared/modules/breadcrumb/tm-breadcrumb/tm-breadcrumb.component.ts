import { ClipboardService } from 'ngx-clipboard';
import { MENU_LIST } from './menu-list';
import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/shared/commonBuildBlocks/services/authSercies/auth.service';
import { DataPassingService } from 'src/app/shared/commonBuildBlocks/services/dataPassingServices/dataPassing.service';
import { ScribeService } from 'src/app/shared/commonBuildBlocks/services/scribeServices/scribe.service';
import { environment } from 'src/environments/environment';
import { ToastMessageService } from 'src/app/shared/commonBuildBlocks/toaster/toast-message.service';
import { ErrorSuccessMessage } from 'src/app/shared/commonBuildBlocks/enum/error-success-message.enum';
import { NotificationService } from 'src/app/shared/commonBuildBlocks/services/notification/notification.service';
import { FeaturesCategoryEnum, UpdateCategoryRequest } from 'src/app/shared/commonBuildBlocks/model/admin/admin-config.model';
import { DoctorService } from 'src/app/shared/commonBuildBlocks/services/doctorServices/doctor.service';

@Component({
  selector: 'app-tm-breadcrumb',
  templateUrl: './tm-breadcrumb.component.html',
  styleUrls: ['./tm-breadcrumb.component.scss'],
})
export class TmBreadcrumbComponent implements OnInit, OnChanges {
  // Logged In User
  checker: any = this.datapassingService.loggingUserName
    ? this.datapassingService.loggingUserName
    : sessionStorage.getItem('USER_ID');
  userID = sessionStorage.getItem('USER_ID');
  // 'User Name';
  enableChecker: string = 'Yes';
  widthToggle = false;
  rotateToggle = false;
  rotateToggle2 = false;
  rotateToggle3 = false;

  // Left Side Menu
  toggleMenu: boolean = true;
  sidebarSubmenuToggle: boolean = true;
  submenuIconToggle: boolean = true;
  sidebarToggle: boolean = false;

  //notify-area
  isNotifying: boolean = false;
  hasDoctorWebsiteLink: boolean;

  @Input() showHomePageOptions: any;

  userLoggedIn: boolean = false;
  userRole = sessionStorage.getItem('ROLE');
  profileDetails: any = null;
  scribeList: any;
  profile: string = 'assets/images/img_avatar.png';
  menus = [];
  changePwdScreen: boolean = false;
  notificationCount = 0;
  unReadNotificationCount = 0;

  constructor(
    private authService: AuthService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private doctorServiceInstance: DoctorService,
    private datapassingService: DataPassingService,
    private routes: ActivatedRoute,
    public scribeService: ScribeService,
    private _clipboardService: ClipboardService,
    public toastMessage: ToastMessageService,
    public notificationService: NotificationService,
  ) { }

  ngOnInit(): void {
    this.getNotifications();
    if (this.userRole === 'DOCTOR') {
      this.fetchCategoryDtlsByDrId();
    }
    this.menus = MENU_LIST[this.authService.getUserRole().toLocaleLowerCase()] || [];

    this.datapassingService.setProfileDetails.subscribe((result) => {
      if (
        this.authService.isUserReceptionist() ||
        this.authService.isUserSystemUser() ||
        this.authService.isUserCallcentre()
      ) {
        this.profileDetails = {};
      } else {
        console.log('result', result);
        this.profileDetails = result ? result : {};
      }
    });
    this.datapassingService.docScribeList.subscribe((result) => {
      this.scribeList = result;
    });

    this.isUserLoggedIn();
    this.checkLoggedInUserRole();
    let profile =
      this.profileDetails.profile != null
        ? this.profileDetails.profile.replace('/var/telemedicine/', `${environment['baseUrl']}`)
        : 'assets/images/img_avatar.png';
    this.profile = profile ? `${profile}` : 'assets/images/img_avatar.png';
    this.datapassingService.profilePhoto = this.profile;
  }

  refreshNotifications(event: boolean) {
    if (event) {
      this.notificationService.getAllNotifications(true);
    }
  }

  getNotifications() {
    this.notificationService.requestPermission();
    this.notificationService.getAllNotifications(false);
    this.notificationService.notifications$.subscribe((res) => {
      this.notificationCount = res?.notificationList?.length || 0;
      this.unReadNotificationCount = res?.count || 0;
    });
  }

  ngOnChanges(change: SimpleChanges) {
    if (this.profileDetails && this.profileDetails.profile) {
      let profile = this.profileDetails.profile.replace('/var/telemedicine/', `${environment['baseUrl']}`);
      this.profile = profile ? `${profile}` : 'assets/images/img_avatar.png';
      this.datapassingService.profilePhoto = this.profile;
    }
  }

  isUserLoggedIn() {
    this.userLoggedIn = this.authService.isLoggedIn();
    // Hide Sign-In and Contact-Us buttons for reset-password page
    this.activatedRoute.url.subscribe((url) => {
      if (url.length > 0) {
        if (url[0].path == 'change-password') {
          this.changePwdScreen = true;
        }
      }
    });
  }

  checkLoggedInUserRole() {
    this.userRole = sessionStorage.getItem('ROLE');
    this.checker = this.datapassingService.loggingUserName
      ? this.datapassingService.loggingUserName
      : sessionStorage.getItem('USER_ID');
  }

  onClickDashboard() {
    if (this.userRole === 'DOCTOR') {
      this.router.navigate(['appointments']);
    } else if (this.userRole === 'PATIENT') {
      this.router.navigate(['patient-dashboard']);
    } else if (this.userRole === 'SCRIBE') {
      this.router.navigate(['appointments']);
    }
  }

  onClickRoute(route) {
    if (route) {
      if (route == 'doctor-appointments') {
        this.router.navigate([route], {
          queryParams: { isBookAppointment: true },
        });
      } else if (route == 'selfAssessmentForm') {
        let assessmentLink = this.profileDetails?.pre_assessment_link;
        if (assessmentLink === assessmentLink ? assessmentLink : null) {
          window.open(assessmentLink, '_blank');
        } else {
          this.toastMessage.showInfoMsg(ErrorSuccessMessage.SELFASSESSMENTLINK, 'Information');
        }
      } else {
        this.router.navigate([route]);
      }
    }
  }

  onClickuploadHeaderFooter() {
    this.router.navigate(['upload-header-footer-template']);
  }

  /**
   * @description Used to store SignOut API call result
   */
  onClickLogout() {
    this.authService.signOut().subscribe((result) => {
      if (result && result.response) {
        sessionStorage.clear();
        this.isUserLoggedIn();
        this.router.navigate(['sign-in']);
        this.datapassingService.scribeList = [];
      } else if (result && result.errors) {
        alert(result.errors[0].message);
      }
    });
  }

  editProfile() {
    if (this.userRole === 'DOCTOR' || this.userRole === 'PATIENT' || this.userRole === 'SCRIBE') {
      this.router.navigate(['../update-profile'], { relativeTo: this.routes });
    } else if (this.userRole === 'SYSTEMUSER' || this.userRole === 'RECEPTIONIST' || this.userRole === 'CALLCENTRE') {
      this.router.navigate(['../coming-soon']);
    }
  }

  ptregister() {
    this.router.navigate(['../register-patient'], { relativeTo: this.routes });
  }

  onClickHome() {
    this.router.navigate(['../sign-in'], { relativeTo: this.activatedRoute });
  }

  onClickSignIn() {
    this.router.navigate(['../sign-in'], { relativeTo: this.activatedRoute });
  }

  navigateToPage(value) {
    if (value === 'home') {
      this.router.navigate(['../'], { relativeTo: this.activatedRoute });
    } else if (value === 'aboutUs') {
      this.router.navigate(['../aboutUs'], { relativeTo: this.activatedRoute });
    } else if (value === 'programs') {
      this.router.navigate(['../programs'], {
        relativeTo: this.activatedRoute,
      });
    }
  }

  onClickPrescription() {
    this.router.navigate(['../upload-header-footer-template'], {
      relativeTo: this.routes,
    });
  }

  onClickAddScribe() {
    this.router.navigate(['../scribe'], { relativeTo: this.routes });
  }

  onClickChangePass() {
    this.router.navigate(['../change-password'], { relativeTo: this.routes });
  }

  falsiExpands() {
    this.menus.forEach((menu) => {
      menu.expanded = false;
    });
  }

  copyText(text: string) {
    this._clipboardService.copyFromContent(text || 'Link not available');
    document.getElementById('custom-tooltip').style.display = 'inline';
    document.execCommand('copy');
    setTimeout(function () {
      document.getElementById('custom-tooltip').style.display = 'none';
    }, 1000);
  }

  /**
   * used to fecth category details
   */
  private fetchCategoryDtlsByDrId() {
    this.doctorServiceInstance.fetchCategoryDtlsByDrId().subscribe((result) => {
      this.checkDoctorFlag(result);
    });
  }

  private checkDoctorFlag(res: UpdateCategoryRequest) {
    const categories = res.category;
    if (categories?.length) {
      categories.forEach((c) => {
        if (c.categoryName === FeaturesCategoryEnum.DOCTOR_WEBSITE_LINK) {
          this.hasDoctorWebsiteLink = c.categoryFlag;
        }
      });
    }
  }
}
