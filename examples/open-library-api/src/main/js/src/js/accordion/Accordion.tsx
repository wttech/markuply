import React from "react";

interface Props {
  name: string
}

function Accordion(props: Props) {

  const { name } = props;

  // console.log(JSON.stringify(props));

  return (
      <div>
        <div>This is supposted to be an accordion</div>
        <div>Name property: {name}</div>
      </div>
  )

}

export default Accordion;