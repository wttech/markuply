import React from "react";
import ReactDOMServer from "react-dom/server";

function renderComponent(componentType) {
  return (props) => {
    let value = JSON.parse(props);
    return ReactDOMServer.renderToString(React.createElement(componentType, value));
  }
}

export default class Markuply {

  constructor() {
    this.registry = {};
  }

  register(name, renderFunction) {
    this.registry[name] = renderFunction;
  }

  registerReact(name, componentType) {
    this.register(name, renderComponent(componentType));
  }

  render(name, props) {
    let renderFunction = this.registry[name];
    return renderFunction(props);
  }

  expose() {
    global.render = this.render.bind(this);
  }

}
