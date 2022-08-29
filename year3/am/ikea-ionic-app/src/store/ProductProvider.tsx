import React, { useReducer, useCallback } from "react";
import PropTypes from "prop-types";
import { getLogger } from "../core";
import { ProductProps } from "./ProductProps";

const log = getLogger("ProductProvider");

type SaveProductFn = (product: ProductProps) => void;
type DeleteProductFn = (id: string) => void;

export interface ProductsState {
  products?: ProductProps[];
  savingError?: string;
  saveProduct?: (product: ProductProps) => void;
  deleteProduct?: (id: string) => void;
}

interface ActionProps {
  type: string;
  payload?: any;
}

const initialState: ProductsState = {
  products: [
    {
      id: "p1",
      name: "scaun",
      desc: "desc scaun",
      company: "ikea",
      quantity: 10,
      category: "dormitor",
      isle: "isle",
    },
    {
      id: "p2",
      name: "masa",
      desc: "desc masa",
      company: "ikea",
      quantity: 100,
      category: "bucatarie",
      isle: "isle",
    },
    {
      id: "p3",
      name: "perete",
      desc: "desc perete",
      company: "ikea",
      quantity: 1000,
      category: "sufragerie",
      isle: "isle",
    },
    {
      id: "p4",
      name: "dulap",
      desc: "desc dulap",
      company: "ikea",
      quantity: 1,
      category: "dormitor",
      isle: "isle",
    },
    {
      id: "p5",
      name: "canapea",
      desc: "desc canapea",
      company: "ikea",
      quantity: 50,
      category: "sufragerie",
      isle: "isle",
    },
  ],
};

const SAVE_PRODUCT = "SAVE_PRODUCT";
const DELETE_PRODUCT = "DELETE_PRODUCT";

const reducer: (state: ProductsState, action: ActionProps) => ProductsState = (
  state,
  { type, payload }
) => {
  switch (type) {
    case SAVE_PRODUCT: {
      const products = [...(state.products || [])];
      const product = payload.product;
      const index = products.findIndex((p) => p.id === product.id);
      if (index === -1) {
        products.push(product);
      } else {
        products[index] = product;
      }
      return { ...state, products: products };
    }
    case DELETE_PRODUCT: {
      const products = [...(state.products || [])];
      const index = products.findIndex((p) => p.id === payload.id);
      if (index !== -1) {
        products.splice(index, 1);
      }
      return { ...state, products: products };
    }
    default:
      return state;
  }
};

export const ProductContext = React.createContext<ProductsState>(initialState);

interface ProductProviderProps {
  children: PropTypes.ReactNodeLike;
}

export const ProductProvider: React.FC<ProductProviderProps> = ({
  children,
}) => {
  const [state, dispatch] = useReducer(reducer, initialState);
  const { products, savingError } = state;
  const saveProduct = useCallback<SaveProductFn>(saveProductCallback, [
    products,
  ]);
  const deleteProduct = useCallback<DeleteProductFn>(deleteProductCallback, []);
  const value = { products, savingError, saveProduct, deleteProduct };
  log("returns");
  return (
    <ProductContext.Provider value={value}>{children}</ProductContext.Provider>
  );

  function saveProductCallback(savedProduct: ProductProps) {
    log("saveProduct");
    if (savedProduct.id === undefined) {
      const id = products ? products.length + 1 : 1;
      savedProduct.id = `p${id}`;
    }
    dispatch({
      type: SAVE_PRODUCT,
      payload: { product: savedProduct },
    });
  }

  function deleteProductCallback(id: string) {
    log("deleteProduct");
    dispatch({ type: DELETE_PRODUCT, payload: { id } });
  }
};
