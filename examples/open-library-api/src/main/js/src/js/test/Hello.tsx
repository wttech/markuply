import React from "react";

interface Props {
  name: string,
  lastName: string
}

function Hello(props: Props) {

  return (
      <div>
        <div>Hello {props.name}!</div>
      </div>
  )

}

export default Hello;