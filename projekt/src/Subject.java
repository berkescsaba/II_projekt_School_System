public enum Subject {
    MATEK(new Teacher(new Name("Semmelweis", "Ignác"))),
    MAGYAR(new Teacher(new Name("Irinyi", "János"))),
    FÖLDRAJZ(new Teacher(new Name("Eötvös", "Loránd"))),
    TÖRTÉNELEM(new Teacher(new Name("Jedlik", "Ányos"))),
    ZENE(new Teacher(new Name("Teller", "Ede"))),
    BIOLÓGIA(new Teacher(new Name("Telkes", "Mária"))),
    KÉMIA(new Teacher(new Name("Tihanyi", "Kálmán"))),
    FIZIKA(new Teacher(new Name("Neumann", "János"))),
    TESTNEVELÉS(new Teacher(new Name("Asboth", "Oszkár")));

    private Teacher teacher;

    Subject(Teacher teacher) {
        this.teacher = teacher;
    }
}
