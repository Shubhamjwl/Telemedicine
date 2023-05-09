import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { environment } from 'src/environments/environment';
import { AppointmentService } from '../../commonBuildBlocks/services/appointmentServices/appointment.service';
import { AuthService } from '../../commonBuildBlocks/services/authSercies/auth.service';
import { DataPassingService } from '../../commonBuildBlocks/services/dataPassingServices/dataPassing.service';
import { MockResponse } from '../../commonBuildBlocks/services/mock';
import { PatientService } from '../../commonBuildBlocks/services/patientServices/patient.service';
import { ScribeService } from '../../commonBuildBlocks/services/scribeServices/scribe.service';
import { ToastMessageService } from '../../commonBuildBlocks/toaster/toast-message.service';

@Component({
  selector: 'app-breadcrumb',
  templateUrl: './breadcrumb.component.html',
  styleUrls: ['./breadcrumb.component.scss']
})
export class BreadcrumbComponent implements OnInit, OnChanges {

  // Logged In User
  checker: any = this.datapassingService.loggingUserName ? this.datapassingService.loggingUserName : sessionStorage.getItem('USER_ID');
  userID = sessionStorage.getItem('USER_ID');
  // 'User Name';
  enableChecker: string = 'Yes';

  // Left Side Menu
  toggleMenu: boolean = true;
  sidebarSubmenuToggle: boolean = true;
  submenuIconToggle: boolean = true;
  sidebarToggle: boolean = false;

  @Input() showHomePageOptions: any;

  userLoggedIn: boolean = false;
  userRole: any;
  profileDetails: any = null;
  scribeList: any;
  // @Input() setProfileDetails: any;
  profile: string = 'assets/images/img_avatar.png';
  // = MockResponse.roleWiseMenues
  menus = [];
  changePwdScreen: boolean = false;
  ndhmFlag: boolean;

  constructor(
    private authService: AuthService,
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private datapassingService: DataPassingService,
    private appointmentService: AppointmentService,
    private toastrMessage: ToastMessageService,
    private routes: ActivatedRoute,
    public scribeService: ScribeService,
    public patientService: PatientService
  ) {
  }

  ngOnInit(): void {
    this.getNdhmFlag();
    this.datapassingService.setProfileDetails.subscribe(result => {
      // this is temporory fix ----- update when call center, systemuser profile module developed
      if (this.authService.isUserReceptionist() || this.authService.isUserSystemUser() || this.authService.isUserCallcentre()) {
        this.profileDetails = {};
      } else {


        this.profileDetails = result ? result : {};

      }
    });
    this.datapassingService.docScribeList.subscribe(result => {
      this.scribeList = result;
    });
    if (this.authService.getUserRole()) {
      this.menus = MockResponse.roleWiseMenues[this.authService.getUserRole().toLocaleLowerCase()];
    } else {
      this.menus = [];
    }


    this.isUserLoggedIn();
    this.checkLoggedInUserRole();
    this.menuVisibility();
    // this.getMenusList();
    //this.profile = this.datapassingService.profilePhoto;
    let profile = this.profileDetails.profile != null ? this.profileDetails.profile.replace('/var/telemedicine/', `${environment["baseUrl"]}`) : 'assets/images/img_avatar.png'
    this.profile = profile ? `${profile}` : 'assets/images/img_avatar.png';
    this.datapassingService.profilePhoto = this.profile;
  }

  getNdhmFlag() {
    this.patientService.getNdhmFlag().subscribe(
      {
        next: (res) => {
          this.ndhmFlag = res.ndhmFlag;
        },
        error: (err) => {
          console.log("enter in error");
        }
      }

    )
  }

  ngOnChanges(change: SimpleChanges) {
    if (this.profileDetails && this.profileDetails.profile) {
      // let profile =  this.profileDetails.replace('/var/telemedicine/', `${environment["baseUrl"]}`)
      let profile = this.profileDetails.profile.replace('/var/telemedicine/', `${environment["baseUrl"]}`)
      this.profile = profile ? `${profile}` : 'assets/images/img_avatar.png';
      this.datapassingService.profilePhoto = this.profile;
      // this.profile = this.profileDetails.profile ? `data:image/jpeg;base64,${this.profileDetails.profile}` : "assets/images/img_avatar.png";
    }

  }

  getMenusList() {
    this.appointmentService.getMenusList().subscribe(result => {
      if (result.status && result.response) {
        this.menus = result.response ? result.response.mainMenu ? result.response.mainMenu : null : null;
      } else if (result.errors) {
        this.toastrMessage.showErrorMsg(result.errors[0].message, 'Error');
      }
    }, error => {
      this.toastrMessage.showErrorMsg(error['errors'][0].message, 'Error');
    })
  }
  isUserLoggedIn() {
    this.userLoggedIn = this.authService.isLoggedIn();

    // Hide Sign-In and Contact-Us buttons for reset-password page
    this.activatedRoute.url.subscribe(url => {
      if (url.length > 0) {
        if (url[0].path == "change-password") {
          this.changePwdScreen = true;
        }
      }
    });
  }

  checkLoggedInUserRole() {
    this.userRole = sessionStorage.getItem('ROLE');
    this.checker = this.datapassingService.loggingUserName ? this.datapassingService.loggingUserName : sessionStorage.getItem('USER_ID');
    // if (userRole == 'Checker') {
    //   this.enableChecker = 'Yes';
    // } else {
    //   this.enableChecker = 'No';
    // }

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
        this.router.navigate([route], { queryParams: { isBookAppointment: true } });
      } else {
        this.router.navigate([route]);
      }

    }

  }
  onClickuploadHeaderFooter() {
    this.router.navigate(['upload-header-footer-template']);

  }

  menuVisibility() {
    if (this.toggleMenu == false) {
      this.toggleMenu = true;
    } else {
      this.toggleMenu = false;
    }
    if (this.sidebarSubmenuToggle == false) {
      this.sidebarSubmenuToggle = true;
    } else {
      this.sidebarSubmenuToggle = false;
    }

    if (this.submenuIconToggle == false) {
      this.submenuIconToggle = true;
    } else {
      this.submenuIconToggle = false;
    }

    if (this.sidebarToggle == false) {
      this.sidebarToggle = true;
    } else {
      this.sidebarToggle = false;
    }
    // $('.menu-collapsed').toggleClass('d-none');
    // $('.sidebar-submenu').toggleClass('d-none');
    // $('.submenu-icon').toggleClass('d-none');
    // $('#sidebar-container').toggleClass('sidebar-expanded sidebar-collapsed');
  }

  /**
   * @description Used to store SignOut API call result
   */
  onClickLogout() {
    this.authService.signOut().subscribe(result => {

      if (result && result.response) {
        sessionStorage.clear();
        this.isUserLoggedIn();
        // this.router.navigate(['']);
        this.router.navigate(['sign-in']);
        this.datapassingService.scribeList = [];
      } else if (result && result.errors) {
        alert(result.errors[0].message)
      }
    })
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
  /**
   * Used to show Contact Us option details
   */
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
      this.router.navigate(['../programs'], { relativeTo: this.activatedRoute });
    }
  }

  onClickPrescription() {
    this.router.navigate(['../upload-header-footer-template'], { relativeTo: this.routes });
  }

  onClickAddScribe() {
    this.router.navigate(['../scribe'], { relativeTo: this.routes });
  }

  onClickChangePass() {
    this.router.navigate(['../change-password'], { relativeTo: this.routes });
  }
  deleteScribe(scribe) {

  }
  //chnage scribe status
  changeStatus(event, item, status) {

    event.stopPropagation();

    if (item.isDefaultScribe == 'Y') {
      return
    }

    let data = {
      api: "active deactive scribe",
      request: {
        scrbUserID: item.scrbUserId,
        scrbdrUserIDfk: this.authService.getUserId(),
        scrbisActive: status
      }
    }
    this.scribeService.changeScribeStatus(data).subscribe(result => {
      if (result.response) {
        this.toastrMessage.showSuccessMsg(result.response, "Congratulation");
        this.scribeService.getScribeListByDoctorID();
      } else if (result.errors) {
        this.toastrMessage.showErrorMsg(result.errors[0].message, 'Error');
      }
    }, error => {
    })
  }
}
