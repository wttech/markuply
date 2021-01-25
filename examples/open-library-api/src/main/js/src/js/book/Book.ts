interface Author {
  name: string
}

interface Cover {
  small: string,
  medium: string,
  large: string
}

interface Book {
  weight: string,
  title: string,
  numberOfPages: number,
  publishDate: string,
  authors: Author[],
  cover: Cover
}

export default Book;