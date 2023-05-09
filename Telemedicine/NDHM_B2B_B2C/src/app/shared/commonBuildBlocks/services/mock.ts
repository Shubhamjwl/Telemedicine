export class MockResponse {
    static patientDetails = {
        "id": "consultation",
        "version": "v1.0",
        "responseTime": "2020-11-21T17:46:50.098",
        "status": true,
        "response": {
            "apptId": "102",
            "height": "5.0",
            "weight": "60.0",
            "bloodgrp": "A+",
            "ptPersonalDetals": {
                "name": "Veena Sharma",
                "mobileNo": "9870089898",
                "emailId": "veena01@gmail.com",
                "gender": "Female",
                "dob": "03 5 1992"
            },
            "address": {
                "address1": "Maheshwar Nagar",
                "address2": "Bhuj Road",
                "address3": "11 Street"
            },
            "ptMedicalDtls": {
                "allergies": [
                    "dust",
                    "meat"
                ],
                "chronicDiseases": [
                    "cold"
                ]
            },
            "ptLifeStyleDtls": {
                smoking: "non-smoker",
                drinking: "none"
            }
            
            // details: [
            //     {
            //         lifestyle : "smoking",
            //         status : "non-smoker"
            //     },{
            //         lifestyle : "drinking",
            //         status : "non"
            //     }
            //  ]
        },
        "errors": null
    }

    static roleWiseMenues = {
      "doctor": [
        {
          "mainMenu": "Dashboard",
          "id": "dashboard",
          "icon": "fa fa-tachometer fa-fw mr-3",
          "route" : "appointments",
          // "subMenu": [
          //   {
          //     "subMenuName": "Dashboard",
          //     "route" : "appointments"
          //   }
          // ]
        },
        {
          "mainMenu": "My Profile",
          "id": "myProfile",
          "icon": "fa fa-user fa-fw mr-3",
          "subMenu": [
            {
              "subMenuName": "View / Modify Details",
              "route" : "update-profile"
            },
            {
              "subMenuName": "De-Registration",
              "route" : "doctor-deregister"
            },
            {
              "subMenuName": "Change Password",
              "route" : "change-password"
            },
            // {
            //   "subMenuName": "Patient List",
            //   "route" : "patient-list"
            // }
          ]
        },
        {
          "mainMenu": "Patient Registration",
          "id": "patientRegistration",
          "icon": "fa fa-sign-in fa-fw mr-3",
          "subMenu": [
            {
              "subMenuName": "Register Patient",
              "route" : "register-patient"
            },
            {
            "subMenuName": "View / Untag Patient List",
            "route" : "patient-list"
            }
          ]
        },
        {
          "mainMenu": "Scribe Management",
          "id": "scribeRegistration",
          "icon": "fa fa-sign-in fa-fw mr-3",
          "subMenu": [
            {
              "subMenuName": "Register Scribe",
              "route" : "scribe"
            },
            {
              "subMenuName": "View / Modify Scribe Details",
              "route" : "coming-soon"
            },
            {
              "subMenuName": "Activate / De-activate Scribe",
              "route" : "scribe-status"
            },
            {
              "subMenuName": "Assign Scribes",
              "route" : "assign-scribe"
            }
          ]
        },
        {
          "mainMenu": "Appointment",
          "id": "appointment",
          "icon": "fa fa-calendar fa-fw mr-3",
          "subMenu": [
            {
              "subMenuName": "Add Slots",
              "route" : "time"
            },
            {
              "subMenuName": "View / Modify Slots",
              "route" : "modify-time-slot"
            },
            {
              "subMenuName": "Book Appointments",
              "route" : "doctor-appointments"
            },
            {
              "subMenuName": "My Appointments",
              "route" : "appointments"
            },
            // {
            //   "subMenuName": "Upcoming Appointments",
            //   "route" : "coming-soon"
            // },
            {
              "subMenuName": "Completed Appointments",
              "route" : "completed-appointments"
            },
            {
              "subMenuName": "Reschedule Appointment",
              "route" : "coming-soon"
            },
            {
              "subMenuName": "Cancel Appointment",
              "route" : "coming-soon"
            },
            {
              "subMenuName": "Historical Report",
              "route" : "appointments-history"
            },
          ]
        },
        // {
        //   "mainMenu": "Consultation",
        //   "id": "consultation",
        //   "icon": "fa fa-user-md fa-fw mr-3",
        //   "route" : "appointments"
        //   // "subMenu": [
        //   //   {
        //   //     "subMenuName": "Consultation",
        //   //     "route" : "appointments"
        //   //   }
        //   // ]
        // },
        // {
        //   "mainMenu": "Header & Footer",
        //   "id": "uploadtemplate",
        //   "icon": "fa fa-header fa-fw mr-3",
        //   "subMenu": [
        //     {
        //       "subMenuName": "Upload Header/Footer",
        //       "route" : "upload-header-footer-template"
        //     }
        //   ]
        // }
      ],
      "scribe": [
        {
          "mainMenu": "Dashboard",
          "id": "dashboard",
          "icon": "fa fa-tachometer fa-fw mr-3",
          "route" : "appointments"
        },
        {
          "mainMenu": "My Profile",
          "id": "myProfile",
          "icon": "fa fa-user fa-fw mr-3",
          "subMenu": [
            {
              "subMenuName": "View Profile",
              "route" : "update-profile"
            },
            {
              "subMenuName": "Change Password",
              "route" : "change-password"
            }
          ]
        },
        {
          "mainMenu": "Patient Registration",
          "id": "patientRegistration",
          "icon": "fa fa-sign-in fa-fw mr-3",
          "subMenu": [
            {
            "subMenuName": "View Patient List",
            "route" : "patient-list"
            }
          ]
        },
        {
          "mainMenu": "Appointment",
          "id": "appointment",
          "icon": "fa fa-calendar fa-fw mr-3",
          "subMenu": [
            {
              "subMenuName": "Add Slots",
              "route" : "time"
            },
            {
              "subMenuName": "View / Modify Slots",
              "route" : "modify-time-slot"
            },
            {
              "subMenuName": "My Appointments",
              "route" : "appointments"
            },
          //   {
          //     "subMenuName": "Completed Appointments",
          //     "route" : "completed-appointments"
          //   },
          //   // {
          //   //   "subMenuName": "Historical Report",
          //   //   "route" : "appointments-history"
          //   // },
          //   // {
          //   //   "subMenuName": "Book Appointments",
          //   //   "route" : "doctor-appointments"
          //   // },
          ]
        },
      ],
      "patient": [
        {
          "mainMenu": "Dashboard",
          "id": "dashboard",
          "icon": "fa fa-tachometer fa-fw mr-3",
          "route" : "patient-dashboard"
        },
        {
          "mainMenu": "My Profile",
          "id": "myProfile",
          "icon": "fa fa-user fa-fw mr-3",
          "subMenu": [
            {
              "subMenuName": "View / Modify Profile",
              "route" : "update-profile"
            },
            {
              "subMenuName": "Change Password",
              "route" : "change-password"
            }
          ]
        },
        {
          "mainMenu": "Appointment",
          "id": "appointment",
          "icon": "fa fa-calendar fa-fw mr-3",
          "subMenu": [
            // {
            //   "subMenuName": "Tagged Doctor List",
            //   "route" : "list"
            // }
            {
              "subMenuName": "Tagged Doctor List",
              "route" : "book-appointment-dashboard"
            },
            {
              "subMenuName": "Booked Appointment",
              "route" : "book-appointment-dashboard"
            },
            {
              "subMenuName": "My Appointments",
              "route" : "patient-dashboard"
            },
            {
              "subMenuName": "Reschedule Appointment",
              "route" : "patient-dashboard"
            },
            {
              "subMenuName": "Cancel Appointment",
              "route" : "coming-soon"
            },
            {
              "subMenuName": "Completed Appointments",
              "route" : "completed-appointments"
            },
            // {
            //   "subMenuName": "Search Doctors",
            //   "route" : "book-appointment-dashboard"
            // },
          ]
        },
        // {
        //   "mainMenu": "Review",
        //   "id": "review",
        //   "icon": "fa fa-tachometer fa-fw mr-3",
        //   "subMenu": [
        //     {
        //       "subMenuName": "Add review",
        //       "route" : "patient-review"
        //     }
        //   ]
        // }
      ],
      "systemuser": [
        {
          "mainMenu": "Dashboard",
          "id": "dashboard",
          "icon": "fa fa-tachometer fa-fw mr-3",
          "route": "/checker"
        },
        {
          "mainMenu": "My Profile",
          "id": "myProfile",
          "icon": "fa fa-user fa-fw mr-3",
          "subMenu": [
            {
              "subMenuName": "View Profile",
              "route" : "coming-soon"
            },
            {
              "subMenuName": "Change Password",
              "route" : "change-password"
            }
          ]
        },
        {
          "mainMenu": "Verify Doctor",
          "id": "verifyDoctor",
          "icon": "fa fa-user fa-fw mr-3",
          "subMenu": [
            {
              "subMenuName": "Verify Doctor Details",
              "route" : "checker"
            },
            {
              "subMenuName": "De-Register Doctor",
              "route" : "./checker/doc-de-register"
            }
          ]
        },
        {
          "mainMenu": "De-Register Doctor",
          "id": "DeRegisterDoctor",
          "icon": "fa fa-user fa-fw mr-3",
          "route" : "./checker/doc-de-register"
        }
      ],
      "receptionist": [
        {
          "mainMenu": "Dashboard",
          "id": "dashboard",
          "icon": "fa fa-tachometer fa-fw mr-3",
          "route" : "appointments",
          "subMenu": []
        },
        {
          "mainMenu": "My Profile",
          "id": "myProfile",
          "icon": "fa fa-user fa-fw mr-3",
          "subMenu": [
            {
              "subMenuName": "View Profile",
              "route" : "coming-soon"
            },
            {
              "subMenuName": "Change Password",
              "route" : "change-password"
            }
          ]
        },
        {
          "mainMenu": "Appointment",
          "id": "appointment",
          "icon": "fa fa-calendar fa-fw mr-3",
          "subMenu": [
            {
              "subMenuName": "View Slots",
              "route" : "modify-time-slot"
            },
            // {
            //   "subMenuName": "Book Appointments",
            //   "route" : "appointments"
            // }
          ]
        },
        
      ],
      "callcentre": [
        {
          "mainMenu": "Dashboard",
          "id": "dashboard",
          "icon": "fa fa-tachometer fa-fw mr-3",
          "route" : "book-appointment-dashboard",
          "subMenu": []
        },        
        {
          "mainMenu": "My Profile",
          "id": "myProfile",
          "icon": "fa fa-user fa-fw mr-3",
          "subMenu": [
            {
              "subMenuName": "View Profile",
              "route" : "coming-soon"
            },
            {
              "subMenuName": "Change Password",
              "route" : "change-password"
            }
          ]
        },     
        {
          "mainMenu": "Appoitments",
          "id": "appoitments",
          "icon": "fa fa-user fa-fw mr-3",
          "subMenu": [
            {
              "subMenuName": "Search Doctors",
              "route" : "book-appointment-dashboard"
            },
            // {
            //   "subMenuName": "Book Apooitments",
            //   "route" : "coming-soon"
            // }
          ]
        },
      ]
    }
  
}
