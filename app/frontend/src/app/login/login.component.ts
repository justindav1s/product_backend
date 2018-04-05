import { Component, OnInit, Output, EventEmitter } from '@angular/core';

import { User } from '../user';
import { UserService } from '../user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  @Output() user: User = new EventEmitter<User>();
  loggedIn : boolean = false;

  constructor(private userService: UserService) { }

  ngOnInit() {
  }

  onSubmit(): void{
    console.log("login");
    console.log("user.username : " + this.user.username);
    console.log("user.password : " + this.user.password);
    this.userService.login(this.user).subscribe( (user: User[]) => {
      console.log("LoginComponent : user : " + user.id + " : " + user.username);
      this.loggedIn = true;
      this.user.emit(user);
    });
  }
  get greeting() { return "Welcome " + user.username + " !"}
  get diagnostic() { return JSON.stringify(this.user); }
}
