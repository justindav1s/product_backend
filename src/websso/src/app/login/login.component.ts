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

  constructor(private keycloakService: KeycloakService) { }

  ngOnInit() {
    console.log("LoginComponent ngOnInit");
  }

  async onSubmit()  {
    console.log("login");
    console.log("user.username : " + this.user.username);
    console.log("user.password : " + this.user.password);
    if (await this.keycloakService.isLoggedIn()) {
      console.log("AppComponent : isLoggedIn")
      this.userDetails = await this.keycloakService.loadUserProfile();
    }
    console.log("LoginComponent : onNewLogin : JSON : " + JSON.stringify(this.userDetails));
    // this.userService.login(this.user).subscribe( (user: User) => {
    //   console.log("LoginComponent : user : " + user.id + " : " + user.username);
    //   this.loggedIn = true;
    //   this.loggedInUser.emit(user);
    //   this.user = user;
    // });
  }
  get greeting() { return "Welcome " + this.user.username + " !"}
  get diagnostic() { return JSON.stringify(this.user); }
}