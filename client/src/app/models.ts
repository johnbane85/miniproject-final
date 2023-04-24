export interface User {
  user_id?: string;
  username: string;
  email: string;
  password: string;
  // userImage?: File;
}

export interface PostUserResponse {
  postUserResponse: string;
}

export interface EditUserResponse {
  editUserResponse: string;
}

export interface DeleteUserResponse {
  deleteUserResponse: string;
}

export interface Location {
  location_id: number;
  location_name: string;
  address: string;
  description: string;
  website_url: string;
  image_1: string;
  image_2: string;
  image_3: string;
}

export interface Comment {
  user_id: string;
  username: string;
  location_name: string;
  text: string;
  comment_date?: Date;
}

export interface PostCommentResponse {
  postCommentResponse: string;
}

export interface LoginResponse {
  isAuth: boolean;
}

export interface Weather {
  text: string;
  icon: string;
  temp_c: number;
}

export interface LoginRecord {
  // user_id:string;
  login_count: string;
  last_login: Date;
}

// export interface PostUserImageResponse {
//   postUserImageResponse: string;
// }
