import { Component, OnInit } from '@angular/core';

import { User } from '../user';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  user: User = new User();
  constructor() { }

  ngOnInit() {
  }

  onSubmit(): void{
    console.log("login");
  }

  get diagnostic() { return JSON.stringify(this.model); }
}
