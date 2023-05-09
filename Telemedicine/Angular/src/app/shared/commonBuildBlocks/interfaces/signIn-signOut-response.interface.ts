export interface ISignInSignOutResponse{
    message: string,
    userId: string,
    token: string,
    role: string,
    passwordChanged: boolean
}