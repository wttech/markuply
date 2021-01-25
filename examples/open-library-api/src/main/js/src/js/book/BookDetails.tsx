import React from "react";
import './bookDetails.css';
import Book from '@/book/Book';

function BookDetails(props: Book) {

  const calculateAuthors = () => {
    return props.authors
        ? props.authors.map(author => author.name).join(", ")
        : '';
  };

  return (
      <React.Fragment>
        <div className="w-25 m-auto">
          <div className="book-details-container">
            <div className="card">
              {props.cover && props.cover.large &&
                <img src={props.cover.large} className="card-img-top" />
              }
              <div className="card-body">
                <h1>{props.title}</h1>
                {props.weight && <div>Weight: {props.weight}</div>}
                {props.numberOfPages && <div>Number of pages: {props.numberOfPages}</div>}
                {props.publishDate && <div>Publish date: {props.publishDate}</div>}
                {props.authors && <div>Authors: {calculateAuthors()}</div>}
                <div>{props.title}</div>
              </div>
            </div>
          </div>
        </div>
      </React.Fragment>
  )

}

export default BookDetails;
