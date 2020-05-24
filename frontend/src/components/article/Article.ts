
export default class Article {
    name: string;
    description: string;
    image: any; //TODO CW: type ???
    location: string;
    insertionDate: Date;

    // ratings: any[] //TODO CW: type ???
    // Uuid
    // Verfügbarkeit
    // Kategorien


    constructor(name: string, description: string, image: any, location: string, insertionDate: Date) {
        this.description = description;
        this.name = name;
        this.image = image;
        this.location = location;
        this.insertionDate = insertionDate
    }
}