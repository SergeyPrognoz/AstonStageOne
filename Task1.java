/*
* Один файл для трех классов вне пакета - специально чтобы было удобнее проводить первое код-ревью.
* Дальше - если будут задания - буду размещать в отдельных файлах и в нужных пакетах
*/

class Book {
    private String title;
    private String author;
    private int year = 0; // Явно указал значение по умолчанию для читаемости
    private boolean isAvailable = true;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return this.year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean getIsAvailable() {
        return this.isAvailable;
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public Book (){
    }

    public Book (String title, String author, int year, boolean isAvailable) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.isAvailable = isAvailable;
    }

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public void borrowBook() {
        if (this.isAvailable) {
            this.isAvailable = false;
        } else {
            System.out.println("Данная книга недоступна. Поднобная информация о книге - ниже:");
            this.displayInfo();
        }
    }

    public void returnBook() {
        this.isAvailable = true;
    }

    public void displayInfo() {
        System.out.println(this.toString());
    }

    @Override
    public String toString(){
        return "Class:Book" + "; " + "title:" + title + "; " + "author:" + author + "; " +
                "year:" + year + "; " + "isAvailable:" + isAvailable;
    }

}

class Library {
    private int lastFreeBookIndex;

    /*
        * Конечно здесь лучше ArrayList, но можно потренироваться и с массивом.
     */
    private Book [] books;

    /*
        Нет конструктора по умолчанию, чтобы обязательно был задан размер библиотеки.
        У нас массив, а не ArrayList.
    */

    public Library(int libraryCapacity){
        lastFreeBookIndex = 0; // Прописал явный дефолт, можно было и не прописывать.
        books = new Book[libraryCapacity]; // В конструкторе явно требуем задать размер библиотеки книг, массив же.
    }

    public void addBook(Book book) {
        if (book==null){
            System.out.println("Пустая (null) книга не была добавлена");
            return;
        }

        if (this.lastFreeBookIndex < this.books.length) {
            books[lastFreeBookIndex] = book;
            lastFreeBookIndex++;
        } else {
            System.out.println("Книга не может быть добавлена в библиотеку. Нет доступных полок для книг.");
        }
    }

    public void printAvailableBooks() {
        System.out.println("Список доступных книг:");
        for (Book book : this.books) {
            if (book!=null && book.getIsAvailable()) {
                book.displayInfo();
            }
        }
    }

    public Book [] findBooksByAuthor(String author) {
        int lastFreeBookByAuthorIndex = 0;

        Book [] booksByAuthor = new Book[lastFreeBookIndex];
        if (author!=null && !author.isEmpty()) {
            for(Book book: this.books){
                if (book != null && book.getAuthor().equals(author)) {
                    booksByAuthor[lastFreeBookByAuthorIndex] = book;
                    lastFreeBookByAuthorIndex++;
                }
            }
        }
        return booksByAuthor;
    }

    public void printBooksByAuthor(String author){
        int numberOfBooks = 0;
        Book [] booksByAuthor = this.findBooksByAuthor(author);
        System.out.println("Список книг автора: " + author + " :");

        for (Book book: booksByAuthor) {
            if (book!=null) {
                book.displayInfo();
                numberOfBooks++;
            }
        }
        if (numberOfBooks == 0){
            System.out.println("В библиотеке нет книг автора:" + author);
        }
    }
}

class Main {
    public static final int CAPACITY = 3;
    public static void main (String [] args) {
        Library library = new Library(CAPACITY);
        Book book;

        /* Проверяем, что ничего не валится при добавлении неинициализированной книги */
        book = null;
        library.addBook(book);

        /* Первый конструктор */
        book = new Book();
        book.setTitle("Сказка о рыбаке и рыбке.");
        book.setAuthor("А.С. Пушкин");
        book.setYear(1830);
        System.out.println("Создал первую книгу:");
        book.displayInfo();

        System.out.println("Взял первую книгу:");
        book.borrowBook();
        book.displayInfo();

        System.out.println("Попытался второй раз взять ту же книгу:");
        book.borrowBook();

        book.returnBook();
        library.addBook(book);

        /* Вторая книга с конструктором, принимающим все поля */
        book = new Book();
        book.setTitle("Сказка о Царе Салтане");
        book.setAuthor("А.С. Пушкин");
        book.setYear(1831);
        book.setIsAvailable(false);
        library.addBook(book);

        /* Третья книга с другим конструктором */
        book = new Book("Преступление и наказание", "Ф.М. Достоевский");
        book.setYear(1866);
        library.addBook(book);

        /* Четвёртая книга уже не влезет в библиотеку */
        book = new Book("Бесы", "Ф.М. Достоевский");
        book.setYear(1872);
        library.addBook(book);

        library.printAvailableBooks();
        library.printBooksByAuthor("А.С. Пушкин");
        library.printBooksByAuthor("М.Ю. Лермонтов");
    }
}