export default class Rating {
    amountOfStars: number;
    text: string;
    author: number;

    constructor(amountOfStars: number, text: string, author: number) {
        this.amountOfStars = amountOfStars;
        this.text = text;
        this.author = author;
    }
}