import Category from "@/components/category/Category";
import Rating from "@/components/rating/Rating";

export default class Article {
    articleId: string;
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


    constructor(name: string,
                description: string,
                image: any,
                location: string,
                insertionDate: Date,
                categories : Category[],
                ratings: Rating[],
                userId: number,
                articleId: string) {
        this.description = description;
        this.name = name;
        this.image = image;
        this.location = location;
        this.insertionDate = insertionDate;
        this.categories = categories;
        this.ratings = ratings;
        this.userId = userId;
        this.articleId = articleId;
    }
}