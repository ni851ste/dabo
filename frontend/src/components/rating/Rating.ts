export default class Rating {
    amountOfStars: number;
    text: string;
    author: number;
    date: Date;

    constructor(amountOfStars: number, text: string, author: number, date: Date) {
        this.amountOfStars = amountOfStars;
        this.text = text;
        this.author = author;
        this.date = date;
    }
}