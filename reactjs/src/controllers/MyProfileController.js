import AppController from './AppController';

class MyProfileController extends AppController {
    getMyLocalProfile() {
        return this.getMe();
    }

    getMyProfile() {
    }
    
    handleMyProfileResponse(data) {
    }
}

export default MyProfileController;