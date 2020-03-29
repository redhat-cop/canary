export class Users {

    username: string;
    shadow_must_change: string;
    encrypted_password: string;
    login_shell: string;
    home_directory: string;
    uid: string;
    gid: string;

    constructor(values: Object = {}){
        Object.assign(this, values);
    }
}
