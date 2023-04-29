import React from 'react';
import { useHistory, BrowserRouter as Router } from "react-router-dom";

const BookEdit = (props) => {

    const history = useHistory();
    const [formData, updateFormData] = React.useState( {
        name: "",
        author: -1,
        availableCopies: -1,
        category: ""
    })

    const handleChange = (e) => {
        updateFormData({
            ...formData,
            [e.target.name]: e.target.value.trim()
        })
    }

    const onFormSubmit = (e) => {
        e.preventDefault();
        const name = formData.name !== "" ? formData.name : props.book.name;
        const category = formData.category.toString() !== "" ? formData.category : props.book.category;
        const availableCopies = formData.availableCopies !== -1 ? formData.availableCopies : props.book.availableCopies;
        const author = formData.author !== -1 ? formData.author : props.book.author.id;

        props.onEditBook(props.book.id, name, author, availableCopies, category);
        history.push("/books");
    }

    return (
        <Router>
            <div className="row mt-5">
                <div className="col-md-5" key={props.book.id}>
                    <form onSubmit={onFormSubmit}>
                        <div className="form-group">
                            <label htmlFor="name">Book name</label>
                            <input type="text"
                                   className="form-control"
                                   id="name"
                                   name="name"
                                   required
                                   placeholder={props.book.name}
                                   onChange={handleChange}
                            />
                        </div>
                        <div className="form-group">
                            <label>Author</label>
                            <select name="author" className="form-control" onChange={handleChange}>
                                {props.authors.map((term) => {
                                        if (props.book.author !== undefined &&
                                            props.book.author.id === term.id)
                                            return <option selected={props.book.author.id}
                                                           value={term.id} key={term.id}>{term.name}</option>
                                        else return <option value={term.id} key={term.id}>{term.name}</option>
                                    }
                                )}
                            </select>
                        </div>
                        <div className="form-group">
                            <label>Category</label>
                            <select name="category" className="form-control" onChange={handleChange}>
                                {props.categories.map((term) => {
                                    if (props.book.category !== undefined &&
                                        props.book.category === term)
                                        return <option selected={props.book.category}
                                                       value={term.toString()}
                                                       key={term.toString()}>{term.toString()}</option>
                                    else return <option value={term.toString()}
                                                        key={term.toString()}>{term.toString()}</option>
                                })}
                            </select>
                        </div>
                        <div className="form-group">
                            <label htmlFor="availableCopies">Available Copies</label>
                            <input type="number"
                                   className="form-control"
                                   id="availableCopies"
                                   name="availableCopies"
                                   required
                                   placeholder={props.book.availableCopies}
                                   onChange={handleChange}/>
                        </div>
                        <button id="submit" type="submit" className="btn btn-primary">Submit</button>
                    </form>
                </div>
            </div>
        </Router>
    );
}

export default BookEdit;
