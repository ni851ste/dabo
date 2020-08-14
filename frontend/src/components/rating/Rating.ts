export default class Rating {
    amountOfStars: number;
    comment: string;
    author: number;
    date: Date;

    constructor(amountOfStars: number, comment: string, author: number, date: Date) {
        this.amountOfStars = amountOfStars;
        this.comment = comment;
        this.author = author;
        this.date = date;
    }
}