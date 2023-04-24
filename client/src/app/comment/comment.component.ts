import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NoSpaceValidator } from '../nospace.validator';
import { Comment } from '../models';
import { ActivatedRoute, Router } from '@angular/router';
import { CommentService } from '../comment.service';
import { authUser } from '../auth/auth.model';

@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.css'],
})
export class CommentComponent implements OnInit {
  form!: FormGroup;
  location_name: string = '';
  userData!: authUser;
  userName = '';
  userId = '';

  constructor(
    private fb: FormBuilder,
    private commentSvc: CommentService,
    private activeRoute: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.userData = JSON.parse(localStorage.getItem('userData')!);
    this.userName = this.userData.username_returned;
    this.userId = this.userData.userId_returned;

    this.location_name = this.activeRoute.snapshot.params['location_name'];
    this.form = this.createForm();
  }

  processForm() {
    const comment = this.form.value as Comment;

    // console.info('comment: ', this.form.value);

    this.commentSvc
      .postComment(comment)
      .then((response) => {
        // console.info(`postCommentResponse: ${response.postCommentResponse}`);
        this.router.navigate(['/location', this.location_name]);
      })
      .catch((error) => {
        console.error('>>> error: ', error);
      });
  }

  createForm(): FormGroup {
    return this.fb.group({
      user_id: this.fb.control(this.userId, [
        Validators.required,
        Validators.minLength(8),
        Validators.maxLength(8),
      ]),
      username: this.fb.control(this.userName, [
        Validators.required,
        NoSpaceValidator.cannotContainSpace,
      ]),
      location_name: this.fb.control(this.location_name, [Validators.required]),
      text: this.fb.control('', [Validators.required, Validators.minLength(4)]),
    });
  }
}
