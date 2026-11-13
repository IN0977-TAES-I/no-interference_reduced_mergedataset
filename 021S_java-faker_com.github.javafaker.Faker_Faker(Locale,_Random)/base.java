// class name : com.github.javafaker.Faker
// method name: Faker(Locale, Random)
// side       : base
// region     : lines 59-70 (affected lines: [59, 69, 70])
class Faker {
    void Faker_Locale__Random_() {
        this.number = new Number(randomService);
        this.internet = new Internet(name, proxiedFakeValueService, randomService);
        this.phoneNumber = new PhoneNumber(proxiedFakeValueService);
        this.address = new Address(this, name, proxiedFakeValueService, randomService);
        this.book = new Book(this, proxiedFakeValueService);
        this.business = new Business(proxiedFakeValueService);
        this.color = new Color(proxiedFakeValueService);
        this.company = new Company(this, proxiedFakeValueService);
        this.options = new Options(randomService);
        this.code = new Code(randomService);
        this.finance = new Finance(proxiedFakeValueService, randomService);
        this.dateAndTime = new DateAndTime(randomService);
    }
}
