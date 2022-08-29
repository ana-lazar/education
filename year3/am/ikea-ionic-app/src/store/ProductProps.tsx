export interface ProductProps {
  id?: string;
  name?: string;
  desc?: string;
  company?: string;
  quantity?: number;
  category?: string;
  isle?: string;
}

export function validateProduct(product?: ProductProps) {
  let err = "";
  if (!product) {
    err += "product is invalid \n";
  }
  if (!product?.name || product?.name === "") {
    err += "name is invalid \n";
  }
  if (!product?.desc || product?.desc === "") {
    err += "desc is invalid \n";
  }
  if (!product?.company || product?.company === "") {
    err += "company is invalid \n";
  }
  if (!product?.category || product?.category === "") {
    err += "category is invalid \n";
  }
  if (!product?.isle || product?.isle === "") {
    err += "isle is invalid \n";
  }
  if (!product?.quantity || product?.quantity === 0) {
    err += "quantity is invalid \n";
  }
  return err;
}
