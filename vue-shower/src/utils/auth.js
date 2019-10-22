/**
 * Created by SongCQ on 2018/9/28.
 */


import Cookies from 'js-cookie'

const DefaultTokenKey = 'Admin-Token'

export function getToken() {
  return Cookies.get(DefaultTokenKey)
}

export function setToken(token) {
  return Cookies.set(DefaultTokenKey, token)
}

export function setTokenByKey(tokenKey,token) {
  return Cookies.set(tokenKey, token)
}

export function removeTokenByKey(tokenKey) {
  return Cookies.remove(tokenKey)
}

export function removeToken() {
  return Cookies.remove(DefaultTokenKey)
}
