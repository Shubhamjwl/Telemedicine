import { ROUTE } from 'src/app/shared/constants/route.enum';

export const MENU_LIST = {
  doctor: [
    {
      name: 'Dashboard',
      property: 'dashboard',
      icon: 'dashboard.svg',
      route: 'appointments',
    },
    {
      name: 'My Profile',
      property: 'myProfile',
      icon: 'my-profile.svg',
      expanded: false,
      subMenus: [
        {
          route: 'update-profile',
          name: 'View / Modify Details',
        },
        {
          route: 'doctor-deregister',
          name: 'De-Registration',
        },
        {
          route: 'change-password',
          name: 'Change Password',
        },
      ],
    },
    {
      name: 'Special Conditions Screening',
      property: 'diagnosticSupport',
      icon: 'Diagnostic Support-heartbeat.png',
      route: 'diagnosticSupport',
    },
    {
      name: 'Self Assessment Form',
      property: 'selfAssessmentForm',
      icon: 'Self Assessment form.svg',
      route: 'selfAssessmentForm',
    },
    {
      name: 'Patient Registration',
      property: 'patientRegistration',
      icon: 'patient-registration.svg',
      expanded: false,
      subMenus: [
        {
          route: 'register-patient',
          name: 'Register Patient',
        },
        {
          route: 'patient-list',
          name: 'View / Untag Patient List',
        },
      ],
    },
    {
      name: 'Prescribe Services',
      property: 'prescribeService',
      icon: 'Prescribe-prescription.svg',
      route: 'prescribeService',
    },
    {
      name: 'Scribe Management',
      property: 'scribeManagement',
      icon: 'scribe-management.svg',
      expanded: false,
      subMenus: [
        {
          route: 'scribe',
          name: 'Register Scribe',
        },
        {
          route: 'coming-soon',
          name: 'View / Modify Scribe Details',
        },
        {
          route: 'scribe-status',
          name: 'Activate / De-activate Scribe',
        },
        {
          route: 'assign-scribe',
          name: 'Assign Scribes',
        },
      ],
    },
    {
      name: 'Appointment',
      property: 'appointment',
      icon: 'appointment.svg',
      expanded: false,
      subMenus: [
        {
          route: 'time',
          name: 'Add Slots',
        },
        {
          route: 'modify-time-slot',
          name: 'View Slots',
        },
        {
          route: 'doctor-appointments',
          name: 'My Appointments',
        },
        {
          route: 'completed-appointments',
          name: 'Completed Appointments',
        },
        {
          route: 'doctor-reschedule-book-appointments',
          name: 'Reschedule/Cancel Appointments',
        },
        {
          route: 'canceled-appointments',
          name: 'Cancelled Appointments',
        },
      ],
    },
    {
      name: 'Order History',
      property: 'orderHistory',
      icon: 'order-history.svg',
      route: 'orderHistory',
    },
  ],
  scribe: [
    {
      name: 'Dashboard',
      property: 'dashboard',
      icon: 'dashboard.svg',
      route: 'appointments',
    },
    {
      name: 'My Profile',
      property: 'myProfile',
      icon: 'my-profile.svg',
      expanded: false,
      subMenus: [
        {
          name: 'View Profile',
          route: 'update-profile',
        },
        {
          name: 'Change Password',
          route: 'change-password',
        },
      ],
    },
    {
      name: 'Patient Registration',
      property: 'patientRegistration',
      icon: 'patient-registration.svg',
      expanded: false,
      subMenus: [
        {
          name: 'View Patient List',
          route: 'patient-list',
        },
      ],
    },
    {
      name: 'Appointment',
      property: 'appointment',
      icon: 'appointment.svg',
      expanded: false,
      subMenus: [
        {
          name: 'Add Slots',
          route: 'time',
        },
        {
          name: 'View / Modify Slots',
          route: 'modify-time-slot',
        },
        {
          name: 'My Appointments',
          route: 'appointments',
        },
      ],
    },
  ],
  patient: [
    {
      name: 'Dashboard',
      property: 'dashboard',
      icon: 'dashboard.svg',
      route: 'patient-dashboard',
    },
    {
      name: 'My Profile',
      property: 'myProfile',
      icon: 'my-profile.svg',
      expanded: false,
      subMenus: [
        {
          name: 'View / Modify Profile',
          route: 'update-profile',
        },
        {
          name: 'Change Password',
          route: 'change-password',
        },
      ],
    },
    {
      name: 'Appointment',
      property: 'appointment',
      icon: 'appointment.svg',
      expanded: false,
      subMenus: [
        {
          name: 'Tagged Doctor List',
          route: 'book-appointment-dashboard',
        },
        {
          name: 'Book Appointment',
          route: 'book-appointment-dashboard',
        },
        {
          name: 'My Appointments',
          route: 'patient-dashboard',
        },
        {
          name: 'Reschedule/Cancel Appointments',
          route: 'reschedule-book-appointments',
        },
        {
          name: 'Cancelled Appointments',
          route: 'canceled-appointments',
        },
        {
          name: 'Completed Appointments',
          route: 'completed-appointments',
        },
      ],
    },
    {
      name: 'Order History',
      property: 'orderHistory',
      icon: 'order-history.svg',
      route: 'orderHistory',
    },
    {
      name: 'Document Management',
      property: 'documentManagement',
      icon: 'Document-upload-icon.png',
      route: 'document-management',
    },
  ],
  systemuser: [
    {
      name: 'Dashboard',
      property: 'dashboard',
      icon: 'dashboard.svg',
      route: '/checker',
    },
    {
      name: 'My Profile',
      property: 'myProfile',
      icon: 'my-profile.svg',
      expanded: false,
      subMenus: [
        {
          name: 'View Profile',
          route: 'coming-soon',
        },
        {
          name: 'Change Password',
          route: 'change-password',
        },
      ],
    },
    {
      name: 'Verify Doctor',
      property: 'verifyDoctor',
      icon: 'patient-registration.svg',
      expanded: false,
      subMenus: [
        {
          name: 'Verify Doctor Details',
          route: 'checker',
        },
        {
          name: 'De-Register Doctor',
          route: './checker/doc-de-register',
        },
      ],
    },
    {
      name: 'De-Register Doctor',
      property: 'DeRegisterDoctor',
      icon: 'patient-registration.svg',
      route: './checker/doc-de-register',
    },
    {
      name: 'Doctor Bulk Registration',
      property: 'bulkDoctor',
      icon: 'patient-registration.svg',
      route: './checker/bulk-upload-doc',
    },
    {
      name: 'Doctor List',
      property: 'checkListDoctor',
      icon: 'patient-registration.svg',
      route: './checker/check-list-doc',
    },
  ],
  receptionist: [
    {
      name: 'Dashboard',
      property: 'dashboard',
      icon: 'dashboard.svg',
      route: 'appointments',
    },
    {
      name: 'My Profile',
      property: 'myProfile',
      icon: 'my-profile.svg',
      expanded: false,
      subMenus: [
        {
          name: 'View Profile',
          route: 'coming-soon',
        },
        {
          name: 'Change Password',
          route: 'change-password',
        },
      ],
    },
    {
      name: 'Appointment',
      property: 'appointment',
      icon: 'appointment.svg',
      expanded: false,
      subMenus: [
        {
          name: 'View Slots',
          route: 'modify-time-slot',
        },
      ],
    },
  ],
  callcentre: [
    {
      name: 'Dashboard',
      property: 'dashboard',
      icon: 'dashboard.svg',
      route: 'book-appointment-dashboard',
    },
    {
      name: 'My Profile',
      property: 'myProfile',
      icon: 'my-profile.svg',
      expanded: false,
      subMenus: [
        {
          name: 'View Profile',
          route: 'coming-soon',
        },
        {
          name: 'Change Password',
          route: 'change-password',
        },
      ],
    },
    {
      name: 'Appointment',
      property: 'appointment',
      icon: 'appointment.svg',
      expanded: false,
      subMenus: [
        {
          name: 'Search Doctors',
          route: 'book-appointment-dashboard',
        },
      ],
    },
  ],
  admin: [
    {
      name: 'Alert/Notification',
      property: 'dashboard',
      icon: 'dashboard.svg',
      route: `${ROUTE.ADMIN}/${ROUTE.ADMIN_DASHBOARD}`,
    },
  ],
};
