import Category from "@/components/category/Category";
import Rating from "@/components/rating/Rating";

export default class Article {
    name: string;
    description: string;
    images: string[];
    location: string;
    insertionDate: Date;
    categories: Category[];
    ratings: Rating[];
    userId: number;
    id: number;
    // Uuid
    // Verfügbarkeit


    constructor(name: string, description: string, images: string[], location: string, insertionDate: Date, categories : Category[], ratings: Rating[], userId: number, id: number) {
        this.description = description;
        this.name = name;
        this.images = images;
        this.location = location;
        this.insertionDate = insertionDate;
        this.categories = categories;
        this.ratings = ratings;
        this.userId = userId;
        this.id = id;
    }
}