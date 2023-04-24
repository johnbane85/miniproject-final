import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Comment, PostCommentResponse } from './models';
import { firstValueFrom } from 'rxjs';

// const BACKEND = 'http://localhost:8080';
const BACKEND = '';

@Injectable()
export class CommentService {
  constructor(private http: HttpClient) {}

  public postComment(comment: Comment): Promise<PostCommentResponse> {
    const form = new FormData();
    form.set('user_id', comment.user_id);
    form.set('username', comment.username);
    form.set('location_name', comment.location_name);
    form.set('text', comment.text);

    return firstValueFrom(
      this.http.post<PostCommentResponse>(`${BACKEND}/api/postComment`, form)
    );
  }

  public getComments(location_name: string): Promise<Comment[]> {
    return firstValueFrom(
      this.http.get<Comment[]>(`${BACKEND}/api/getComments/${location_name}`)
    );
  }
}
