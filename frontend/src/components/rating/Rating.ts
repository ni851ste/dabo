export default class Rating {
    amountOfStars: number;
    header: string;
    text: string;
    author: number;
    date: Date;

    constructor(amountOfStars: number, header: string, text: string, author: number, date: Date) {
        this.amountOfStars = amountOfStars;
        this.header = header
        this.text = text;
        this.author = author;
        this.date = date;
    }
}