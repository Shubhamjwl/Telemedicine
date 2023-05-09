import { MatDialogModule } from '@angular/material/dialog';
import { NotificationListComponent } from './notification-list/notification-list.component';
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTooltipModule } from '@angular/material/tooltip';
import { BreadcrumbRoutingModule } from './breadcrumb-routing.module';
import { BreadcrumbComponent } from './breadcrumb.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { TmBreadcrumbComponent } from './tm-breadcrumb/tm-breadcrumb.component';
import { MatButtonModule } from '@angular/material/button';
import { MatTabsModule } from '@angular/material/tabs';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatIconModule } from '@angular/material/icon';
import { HoverClassDirective } from './hover-class.directive';
import { ExpandWidthDirective } from './expand-width.directive';
import { NotificationComponent } from './notification-list/notification/notification.component';
import { AngularFireModule } from '@angular/fire';
import { environment } from 'src/environments/environment';
import { AngularFireMessagingModule } from '@angular/fire/messaging';
import { NgxSpinnerModule } from 'ngx-spinner';

@NgModule({
  declarations: [
    BreadcrumbComponent,
    TmBreadcrumbComponent,
    HoverClassDirective,
    ExpandWidthDirective,
    NotificationListComponent,
    NotificationComponent,
  ],
  imports: [
    CommonModule,
    BreadcrumbRoutingModule,
    NgbModule,
    MatTooltipModule,
    MatIconModule,
    MatExpansionModule,
    MatSidenavModule,
    MatButtonModule,
    MatTabsModule,
    MatDialogModule,
    AngularFireModule.initializeApp(environment.FirebaseConfig),
    AngularFireMessagingModule,
    NgxSpinnerModule
  ],
  exports: [BreadcrumbComponent, TmBreadcrumbComponent],
})
export class BreadcrumbModule { }
