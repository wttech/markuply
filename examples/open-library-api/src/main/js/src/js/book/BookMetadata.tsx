import React from "react";
import Book from '@/book/Book';

function BookMetadata(props: Book) {

  return (
      <React.Fragment>
        <title>
          {props.title}
        </title>
        <meta property="og:title" content={props.title} />
      </React.Fragment>
  )

}

export default BookMetadata;