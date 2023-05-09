import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RegisterScribeComponent } from './register-scribe/register-scribe.component';

const routes: Routes = [
  {
    path: '',
    component: RegisterScribeComponent, data : {user : 'scribe'}
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class RegisterScribeRoutingModule { }
