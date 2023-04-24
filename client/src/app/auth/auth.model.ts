export interface authModel {
  auth_state: boolean;
  userId_returned: string;
  username_returned: string;
  email_returned: string;
  _token: string;
  expiresIn: Date;
}

export class authUser {
  constructor(
    public userId_returned: string,
    public username_returned: string,
    public email_returned: string,
    private _token: string,
    private _tokenExpiry: Date
  ) {}

  get token() {
    // Check if the token has expired
    if (!this._tokenExpiry || new Date() > this._tokenExpiry) {
      return null;
    }
    return this._token;
  }
}
