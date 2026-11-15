class Faker {
    RandomService randomService;
    FakeValuesService fakeValuesService;
    Lorem lorem;
    Name name;
    Number number;
    Internet internet;
    PhoneNumber phoneNumber;
    Address address;
    Business business;
    Book book;
    Color color;
    Company company;
    Options options;
    Code code;
    Finance finance;
    DateAndTime dateAndTime;

    public Faker(Locale locale, Random random) {
        this.randomService = new RandomService(random);
        this.fakeValuesService = new FakeValuesService(locale, randomService);
        FakeValuesService defaultEnglishFakeValuesService = new FakeValuesService(locale.ENGLISH, randomService);
        FakeValuesServiceInterface proxiedFakeValueService = createProxiedFakeValuesService(fakeValuesService,
                defaultEnglishFakeValuesService);

        this.lorem = new Lorem(proxiedFakeValueService, randomService);
        this.name = new Name(this, proxiedFakeValueService);
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
