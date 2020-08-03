import Address from "@/components/user/Address";
import Rating from "@/components/rating/Rating";

export default class User {
    id: string;
    email: string;
    password: string;
    firstName: string;
    lastName: string;
    address: Address;
    picture: string;
    lastNameVisible: boolean;

    ratings: Rating[];
    insertedArticlesId: number[];
    borrowedArticlesId: number[];
    pinnedArticledId: number[];

    constructor(id: string, email: string, password: string, firstname: string, lastname: string, picture: string,
                lastnameVisible: boolean, address: Address, ratings: Rating[], insertedArticlesId: number[],
                borrowedArticlesId: number[], pinnedArticledId: number[]) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstname;
        this.lastName = lastname;
        this.picture = picture;
        this.lastNameVisible = lastnameVisible;
        this.address = address;
        this.ratings = ratings;
        this.insertedArticlesId = insertedArticlesId;
        this.borrowedArticlesId = borrowedArticlesId;
        this.pinnedArticledId = pinnedArticledId;
    }
}
