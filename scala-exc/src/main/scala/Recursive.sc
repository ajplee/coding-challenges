def warmup (x: Int) : Int = {
  if(x == 0) {
    1
  }
  else {
    2 * warmup(x - 1)
  }
}