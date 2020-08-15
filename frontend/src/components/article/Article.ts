import Category from "@/components/category/Category";
import Rating from "@/components/rating/Rating";

export default class Article {
    name: string;
    description: string;
    image: string[]; //TODO CW: type ???
    location: string;
    insertionDate: Date;
    categories: Category[];
    ratings: Rating[];
    userId: number;
    // Uuid
    // Verfügbarkeit


    constructor(name: string, description: string, image: string[], location: string, insertionDate: Date, categories : Category[], ratings: Rating[], userId: number) {
        this.description = description;
        this.name = name;
        this.image = image;
        this.location = location;
        this.insertionDate = insertionDate;
        this.categories = categories;
        this.ratings = ratings;
        this.userId = userId;
    }
}