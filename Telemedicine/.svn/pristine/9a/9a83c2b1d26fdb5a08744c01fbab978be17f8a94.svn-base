import { Injectable } from '@angular/core';
import { FormControl } from '@angular/forms';
@Injectable({
  providedIn: 'root'
})
//patient max length 30
export class CustomValidators {
    static fullNameValidator(maxLength){
        return (control: FormControl) => {  
            const name = control.value;
           const re = new RegExp(/^[a-zA-Z ]*$/);
          //const re = new RegExp(/^(?=.*[0-9])(?=.*[a-zA-Z])([a-zA-Z0-9]+)$/g);
            if(name){
                if(name.length > maxLength){
                    return { 
                        maxlength: true
                    };
                } else if(!re.test(name)) {
                    return {
                        pattern: true
                    }
                }
            }
            return null;
        }
    } 
    static mobileValidator(control: FormControl){
      
        const number = control.value;
        const re = new RegExp(/^[6-9]\d{9}$/);
        if(number){
            if(number.length > 10){
                return { 
                    maxlength: true
                };
            } else if( !re.test(number)) {
                return {
                    pattern: true
                }
            }
        }
        return null;
    }
    static userNameValidator(control: FormControl){
       const name = control.value;
       const re = new RegExp(/^([A-Za-z]{1}[A-Za-z0-9]{7,24})$/g); 
        if(name){
            if(name.length < 8){
                return { 
                    minlength: true
                };
            }else if(name.length > 25){
                return { 
                    maxlength: true
                };
            } else if(!re.test(name)) {
                return {
                    pattern: true
                };
            }
        }
        return null;
    }   
    public static smcValidators(control: FormControl){
        const name = control.value;
        const re = new RegExp(/^[a-zA-Z][a-zA-Z0-9]*(?:\s+[a-zA-Z][a-zA-Z0-9]+)?$/g); 
          if(name){
              if(name.length > 25){
                  return { 
                      maxlength: true
                  };
              } else if(!re.test(name)) {
                  return {
                      pattern: true
                  }
              }
          }
          return null;
    }
    public static numberOnlyValidator(control: FormControl){
        const name = control.value;
        const re = new RegExp(/^[0-9]*$/g); 
          if(name){
              if(name.length > 5){
                  return { 
                      maxlength: true
                  };
              } else if(!re.test(name)) {
                  return {
                      pattern: true
                  }
              }
          }
          return null;
    }
    public static reEnterPassword (control: FormControl) {
        if(!control || !control.parent){
          return null;
        }
        let group = control.parent;
        let password = group.get('password').value;
    
        if (password != control.value){
            return {passwordMismatch: true};
        }
          
        return null;
          
     };

    public static abhaIdValidators(control: FormControl){
        const name = control.value;
        const re = new RegExp(/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)+$/); 
          if(name){
              if(name.length > 25){
                  return { 
                      maxlength: true
                  };
              } else if(!re.test(name)) {
                  return {
                      pattern: true
                  }
              }
          }
          return null;
    }

    static aadhaarValidator(control: FormControl){
        const name = control.value;
        const re =new RegExp(/^[0-9]{12,12}$/); 
        if(name){
            if(name.length > 25){
                return { 
                    maxlength: true
                };
            } else if(!re.test(name)) {
                return {
                    pattern: true
                }
            }
        }
        return null;
    }

    static otpValidator(control: FormControl){
        const name = control.value;
        const re = new RegExp(/^[0-9]{6,6}$/); 
        if(name){
            if(name.length > 25){
                return { 
                    maxlength: true
                };
            } else if(!re.test(name)) {
                return {
                    pattern: true
                }
            }
        }
        return null;
    }
}
