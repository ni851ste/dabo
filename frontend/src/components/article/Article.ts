import Category from "@/components/category/Category";

export default class Article {
    name: string;
    description: string;
    image: any; //TODO CW: type ???
    location: string;
    insertionDate: Date;
    category: Category[]

    // ratings: any[] //TODO CW: type ???
    // Uuid
    // Verfügbarkeit


    constructor(name: string, description: string, image: any, location: string, insertionDate: Date, category : Category[]) {
        this.description = description;
        this.name = name;
        this.image = image;
        this.location = location;
        this.insertionDate = insertionDate
        this.category = category
    }
}