import { Component, OnInit, Output, EventEmitter } from '@angular/core';

import { User } from '../model/user';
import { UserService } from '../services/user.service';

import { KeycloakProfile } from 'keycloak-js';
import { KeycloakService } from 'keycloak-angular';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  user : User = new User();
  @Output() loggedInUser = new EventEmitter<User>();
  loggedIn : boolean = false;
  userDetails: KeycloakProfile;

   // constructor(private userService: UserService) { }

  constructor(private keycloakService: KeycloakService, private userService: UserService) { }

  async ngOnInit() {
    console.log("LoginComponent ngOnInit");
    if (await this.keycloakService.isLoggedIn()) {
      console.log("AppComponent : isLoggedIn")
      this.userDetails = await this.keycloakService.loadUserProfile();
      this.user.username = this.userDetails.username;
      this.user.firstName = this.userDetails.firstName;
      this.user.lastName = this.userDetails.lastName;
      this.user.email = this.userDetails.email;
      // this.loggedIn = true;
      // this.loggedInUser.emit(this.user);
      this.userService.login(this.user).subscribe( (user: User) => {
        console.log("LoginComponent : user : " + user.id + " : " + user.username);
        this.loggedIn = true;
        this.user.id = user.id;
        this.user.basketId = user.basketId;
        this.loggedInUser.emit(this.user);
      });

    }
    console.log("LoginComponent userDetails : JSON : " + JSON.stringify(this.userDetails));
    console.log("LoginComponent user : JSON : " + JSON.stringify(this.user));
  }

  async onSubmit()  {
    console.log("login");
    console.log("user.username : " + this.user.username);
    console.log("user.password : " + this.user.password);
    // if (await this.keycloakService.isLoggedIn()) {
    //   console.log("AppComponent : isLoggedIn")
    //   this.userDetails = await this.keycloakService.loadUserProfile();
    //   this.user.username = this.userDetails.username;
    //   this.user.firstName = this.userDetails.firstName;
    //   this.user.lastName = this.userDetails.lastName;
    //   this.user.email = this.userDetails.email;
    //   this.loggedIn = true;
    //   this.loggedInUser.emit(this.user);
    // }
    // console.log("LoginComponent userDetails : JSON : " + JSON.stringify(this.userDetails));
    // console.log("LoginComponent user : JSON : " + JSON.stringify(this.user));
  }
  get greeting() { return "Welcome " + this.user.firstName + " " + this.user.lastName}
  get diagnostic() { return JSON.stringify(this.user); }
}
