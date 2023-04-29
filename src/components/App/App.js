import './App.css'
import React, {Component} from "react";
import Header from '../Header/header'
import {BrowserRouter as Router, Route} from "react-router-dom";
import Books from '../Books/BookList/books';
import Categories from '../Categories/categories'
import BookAdd from '../Books/BookAdd/addBook'
import BookEdit from '../Books/BookEdit/editBook'
import BooksService from "../../repository/booksRepository";
import Authors from "../Authors/authors";

class App extends Component {

  constructor(props) {
    super(props);
    this.state = {
      books: [],
      authors: [],
      categories: [],
      selectedBook: {}
    }
  }

  render() {
    return (
        <Router>
          <main>
              <Header/>
            <div className={"container"}>
              <Route path={"/books"} exact
                     render={() => <Books books={this.state.books}
                                          onDelete={this.deleteBook}
                                          onEdit={this.getBook}
                                          onMarkAsTaken={this.markAsTaken}/>}/>

              <Route path={"/books/add"} exact render={() => <BookAdd authors={this.state.authors}
                                                                      categories={this.state.categories}
                                                                      onAddBook={this.addBook}/>}/>
              <Route path={"/books/edit/:id"} exact
                     render={() => <BookEdit categories={this.state.categories}
                                             authors={this.state.authors}
                                             onEditBook={this.editBook}
                                             book={this.state.selectedBook}/>}/>
              <Route path={"/categories"} exact render={() => <Categories categories={this.state.categories}/>}/>
              <Route path={"/authors"} exact render={() => <Authors authors={this.state.authors}/>}/>

              <Route path={"/"} exact render={() => <Books books={this.state.books}
                                                           onDelete={this.deleteBook}
                                                           onMarkAsTaken={this.markAsTaken}
                                                           onEdit={this.getBook}/>}/>
              {/*<Redirect to={"/books"}/>*/}
            </div>
          </main>
        </Router>
    );
  }


  // componentDidMount() {
  //   this.loadAuthors();
  //   this.loadBooks();
  //   this.loadCategories();
  // }

  componentDidMount() {
    this.fetchData()
  }

  fetchData = () => {
    this.loadBooks();
    this.loadCategories();
    this.loadAuthors();
  }


  loadBooks = () => {
    BooksService.fetchBooks()
        .then((data) => {
          this.setState({
            books: data.data
          })
        })
  }

  loadCategories = () => {
      BooksService.fetchCategories()
        .then((data) => {
          this.setState({
            categories: data.data
          })
        })
  }

  loadAuthors = () => {
      BooksService.fetchAuthors().then((data) => {
          this.setState({
              authors: data.data
          })
      })
  }

  deleteBook = (id) => {
      BooksService.deleteBook(id)
        .then(() => {
          this.loadBooks();
        });
  }

  markAsTaken = (id) => {
      BooksService.markAsTaken(id)
        .then(() => {
          this.loadBooks();
        });
  }

  addBook = (name, author, availableCopies, category) => {
      BooksService.addBook(name, author, availableCopies, category)
        .then(() => {
          this.loadBooks();
        })
  }
  getBook = (id) => {
      BooksService.getBook(id)
        .then((data) => {
          this.setState({
            selectedBook: data.data
          });
        })
  }
  editBook = (id, name, author, availableCopies, category) => {
      BooksService.editBook(id, name, author, availableCopies, category)
        .then(() => {
          this.loadBooks();
        })
  }
}

export default App;