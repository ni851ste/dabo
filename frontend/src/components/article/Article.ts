import Category from "@/components/category/Category";
import Rating from "@/components/rating/Rating";

export default class Article {
    name: string;
    description: string;
    image: any; //TODO CW: type ???
    location: string;
    insertionDate: Date;
    category: Category[];
    ratings: Rating[] ;
    userId: number;
    // Uuid
    // Verfügbarkeit


    constructor(name: string, description: string, image: any, location: string, insertionDate: Date, category : Category[], ratings: Rating[], userId: number) {
        this.description = description;
        this.name = name;
        this.image = image;
        this.location = location;
        this.insertionDate = insertionDate;
        this.category = category;
        this.ratings = ratings;
        this.userId = userId;
    }
}