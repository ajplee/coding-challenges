import json
import requests
import sys
import math

from collections import OrderedDict

def get(url, id, page):
    try:
        response = requests.get(url.format(id, page))
    except Exception as e:
        print(e)
    
    return response.json()

def calculateCollectionTotal(url, id, collection, discount_value):
    response = get(url, id, 1)
    
    totalPages = math.ceil(response['pagination']['total']/response['pagination']['per_page'])
    
    cartTotal = 0
    totalDiscount = 0
    
    for page in range(1, totalPages+1):
        
        response = get(url, id, page)
        
        for product in response['products']:
            cartTotal += product['price'] 
            if product.get('collection', False) == collection:
                if product['price'] >= discount_value:
                    totalDiscount += discount_value
                else:
                    totalDiscount += product['price']
    
    return OrderedDict([('total_amount', cartTotal),('total_after_discount', cartTotal - totalDiscount)])    
    
def calculateProductTotal(url, id, product_value, discount_value):
    response = get(url, id, 1)
    
    totalPages = math.ceil(response['pagination']['total']/response['pagination']['per_page'])

    cartTotal = 0
    totalDiscount = 0
    
    for page in range(1, totalPages+1):
        response = get(url, id, page)
        
        for product in response['products']:
            cartTotal += product['price']
            if product['price'] >= product_value:
                if product['price'] >= discount_value:
                    totalDiscount += discount_value
                else:
                    totalDiscount += product['price']
    
    return OrderedDict([('total_amount', cartTotal),('total_after_discount', cartTotal - totalDiscount)])

def calculateCartTotal(url, id, cart_value, discount_value):
    response = get(url, id, 1)
    
    totalPages = math.ceil(response['pagination']['total']/response['pagination']['per_page'])

    cartTotal = 0
    
    for page in range(1, totalPages+1):
        response = get(url, id, page)
        
        for product in response['products']:
            cartTotal += product['price']
    
    if cartTotal >= cart_value:
        return OrderedDict([('total_amount', cartTotal),('total_after_discount', cartTotal - discount_value)])
    else:
        return OrderedDict([('total_amount', cartTotal),('total_after_discount', cartTotal)])


def main():
    
    url = "https://backend-challenge-fall-2018.herokuapp.com/carts.json?id={}&page={}"
    
    stdInput = json.loads(input())
    
    cartID = stdInput['id']
    discount_type = stdInput['discount_type']
    discount_value = stdInput['discount_value']
    
    #assuming collections only applies to discount_type: product
    if ('collection' in stdInput.keys()):       
        print(json.dumps(calculateCollectionTotal(url, cartID, stdInput['collection'], discount_value), indent = 2))
    
    #assuming product_value only applies to discount_type: product 
    elif ('product_value' in stdInput.keys()):  
        print(json.dumps(calculateProductTotal(url, cartID, stdInput['product_value'], discount_value), indent = 2))
    
    #assuming cart_value only applies to discount_type: cart
    else:
        print(json.dumps(calculateCartTotal(url, cartID, stdInput['cart_value'],discount_value), indent = 2))

if _name_ == '_main_':
    main()