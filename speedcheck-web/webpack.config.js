const packageJSON = require('./package.json');
const path = require('path');
const webpack = require('webpack');
const CleanWebpackPlugin = require('clean-webpack-plugin');

const BUILD_PATH = path.join(__dirname, 'target', 'build');

module.exports = {
  entry: './app/app.js',

  output: {
    path: BUILD_PATH,
    publicPath: '/',
    filename: 'app-bundle.js'
  },

  devtool: "source-map",

  devServer: {
      proxy: {
        '/api': 'http://localhost:8080/'
      }
  },

  module: {
      rules: [
          {
            test: /\.js$/,
            exclude: /(node_modules)/,
            use: {
              loader: 'babel-loader',
              options: {
                presets: ['env']
              }
            }
          },
          {
            test: /\.(html)$/,
            use: {
              loader: 'html-loader',
              options: {
                minimize : true
              }
            }
          },
          {
              test: /\.css$/,
              use: [ 'style-loader', 'css-loader' ]
          },
          {
              test: /\.(eot|svg|ttf|woff|woff2)$/,
              loader: 'file-loader?name=public/fonts/[name].[ext]'
          },
          {
            test: /\.html$/,
            loader: "htmllint-loader",
            enforce: "pre",
            exclude: /(node_modules)/,
            query: {
              config: '.htmllintrc',
              failOnError: true,
              failOnWarning: false,
            }
          },
          {
              test: /\.js$/,
              loader: "eslint-loader",
              enforce: "pre",
              exclude: /(node_modules)/
          }
        ]
    },

    plugins: [
      new CleanWebpackPlugin([BUILD_PATH], {}),
      new webpack.ProvidePlugin({
        $: "jquery",
        jQuery: "jquery",
        "window.jQuery": "jquery"
      })
    ]

};