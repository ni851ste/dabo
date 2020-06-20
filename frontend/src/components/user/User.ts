import Address from "@/components/user/Adress";
import Rating from "@/components/rating/Rating";

export default class User {
    uuid: number;
    email: string;
    password: string;
    firstname: string;
    lastname: string;
    address: Address;
    picture: string;
    lastNameVisible: boolean;

    ratings: Rating[];
    insertedArticlesId: number[];
    borrowedArticlesId: number[];
    pinnedArticledId: number[];
    friendsId: number[];

    constructor(uuid: number, email: string, password: string, firstname: string, lastname: string, picture: string,
                lastnameVisible: boolean, address: Address, ratings: Rating[], insertedArticlesId: number[],
                borrowedArticlesId: number[], pinnedArticledId: number[], friendsId: number[]) {
        this.uuid = uuid;
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.picture = picture;
        this.lastNameVisible = lastnameVisible;
        this.address = address
        this.ratings = ratings;
        this.insertedArticlesId = insertedArticlesId;
        this.borrowedArticlesId = borrowedArticlesId;
        this.pinnedArticledId = pinnedArticledId;
        this.friendsId = friendsId;
    }
}
