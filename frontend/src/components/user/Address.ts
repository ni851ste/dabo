export default class Address {
    street: string;
    plz: string;
    city: string;
    country: string;
    streetVisible: boolean;

    constructor(street: string, plz: string, city: string, country: string, streetVisible: boolean) {
        this.street = street;
        this.plz = plz;
        this.city = city;
        this.country = country;
        this.streetVisible = streetVisible;
    }
}