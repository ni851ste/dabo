export default class Adress {
    street: string;
    plz: string;
    city: string;
    streetVisible: boolean

    constructor(street: string, plz: string, city:string, streetVisible: boolean) {
        this.street = street;
        this.plz = plz;
        this.city = city;
        this.streetVisible = streetVisible
    }
}