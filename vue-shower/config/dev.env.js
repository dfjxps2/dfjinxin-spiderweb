'use strict'
const merge = require('webpack-merge')
const prodEnv = require('./prod.env')

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  BASE_API:'"http://127.0.0.1:8080/spider"'
  // BASE_API:'"http://192.168.2.157:8080/service"'
})
