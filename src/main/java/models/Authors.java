package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Authors {
    private String authorName;
    private int id;
//    private static ArrayList<Authors> allAuthors = new ArrayList<>();
//    private List<Books> authorBooks;

    public Authors(String authorName){
        this.authorName = authorName;
//        allAuthors.add(this);
//        this.id = allAuthors.size();
//        this.authorBooks = new ArrayList<Books>();
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
    public String getAuthorName() {
        return authorName;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Authors authors = (Authors) o;
        return id == authors.id &&
                authorName.equals(authors.authorName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorName, id);
    }




//    public static Authors findAuthor(int id){
//        return allAuthors.get(id-1);
//    }
//    public void addBookToAuthor(Books newBook){
//        authorBooks.add(newBook);
//    }
//    public List<Books> getAllThisAuthorBooks(){
//        return authorBooks;
//    }
//    public static ArrayList<Authors> getAllAuthors() {
//        return allAuthors;
//    }




}
