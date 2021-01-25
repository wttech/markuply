/* eslint-disable */
const path = require("path");
const webpack = require("webpack");
const MiniCssExtractPlugin = require("mini-css-extract-plugin");

module.exports = {
  entry: './src/js/index.js',
  module: {
    rules: [
      {
        test: /\.(js|jsx)$/,
        exclude: /node_modules/,
        use: {
          loader: "babel-loader",
          options: {
            presets: ["@babel/preset-env", "@babel/preset-react"]
          }
        }
      },
      {
        test: /\.css$/,
        exclude: /node_modules/,
        use: [
          {
            loader: MiniCssExtractPlugin.loader,
            options: {
              publicPath: path.resolve(__dirname, '../resources/public/')
            }
          },
          {
            loader: 'css-loader',
            options: {importLoaders: 1},
          },
        ],
      },
    ]
  },
  resolve: {
    extensions: ["*", ".js", ".jsx", ".css"],
    alias: {
      '@': path.join(__dirname, 'src', 'js')
    }
  },
  output: {
    path: path.resolve(__dirname, "../resources/public/"),
    publicPath: "/",
    filename: "bundle.js"
  },
  devServer: {
    contentBase: path.resolve(__dirname, "../resources/public/"),
    port: 3000,
    publicPath: "/",
    historyApiFallback: {
      index: '/'
    },
    index: '',
    liveReload: false,
    writeToDisk: true
  },
  plugins: [
    // new webpack.HotModuleReplacementPlugin()
      new MiniCssExtractPlugin({
        filename: '[name].bundle.css',
        chunkFilename: '[id].css'
      })
  ]
};
