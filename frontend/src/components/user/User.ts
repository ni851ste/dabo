import Adress from "@/components/user/Adress";
import Rating from "@/components/rating/Rating";

export default class User {
    uuid: number;
    email: string;
    password: string;
    firstname: string;
    lastname: string;
    adress: Adress;
    picture: string;
    lastNameVisible: boolean;

    ratings: Rating[];
    insertedArticlesId: number[];
    borrowedArticlesId: number[];
    pinnedArticledId: number[];
    friendsId: number[];

    constructor(uuid: number, email: string, password: string, firstname: string, lastname: string, picture: string,
                lastnameVisible: boolean, adress: Adress, ratings: Rating[], insertedArticlesId: number[],
                borrowedArticlesId: number[], pinnedArticledId: number[], friendsId: number[]) {
        this.uuid = uuid;
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.picture = picture;
        this.lastNameVisible = lastnameVisible;
        this.adress = adress
        this.ratings = ratings;
        this.insertedArticlesId = insertedArticlesId;
        this.borrowedArticlesId = borrowedArticlesId;
        this.pinnedArticledId = pinnedArticledId;
        this.friendsId = friendsId;
    }
}
