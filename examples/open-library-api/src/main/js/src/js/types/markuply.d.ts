declare namespace NodeJS {
  export interface Global {
    render: (name: string, props: string) => string
  }
}