import React, { useCallback } from "react";
import {
  IonItem,
  IonItemSliding,
  IonLabel,
  IonItemOptions,
  IonItemOption,
  IonNote,
  useIonToast,
  useIonAlert,
} from "@ionic/react";
import { ProductProps } from "./ProductProps";
import { getLogger } from "../core";

type EditProductFn = (id?: string) => void;

const log = getLogger("Product");

interface ProductPropsExt extends ProductProps {
  onEdit: (id?: string) => void;
  onDelete: (id?: string) => void;
}

const Product: React.FC<ProductPropsExt> = ({
  id,
  name,
  company,
  quantity,
  onEdit,
  onDelete,
}) => {
  const [presentToast] = useIonToast();
  const [presentAlert] = useIonAlert();
  const handleEdit = useCallback<EditProductFn>(editProductCallback, [onEdit]);
  const handleDelete = (id?: string) => {
    presentAlert({
      header: "Delete",
      message: `Do you want to delete ${name}?`,
      buttons: [
        "No",
        {
          text: "Yes",
          handler: () => {
            onDelete(id);
            presentToast(`Deleted product with id ${id}`, 3000);
          },
        },
      ],
    });
  };
  log("render");
  return (
    <IonItemSliding>
      <IonItemOptions side="start">
        <IonItemOption onClick={() => handleEdit(id)}>Edit</IonItemOption>
      </IonItemOptions>
      <IonItem>
        <IonLabel>{name}</IonLabel>
        <IonLabel>{company}</IonLabel>
        <IonNote slot="end">{quantity}</IonNote>
      </IonItem>
      <IonItemOptions side="end">
        <IonItemOption color="danger" onClick={() => handleDelete(id)}>
          Delete
        </IonItemOption>
      </IonItemOptions>
    </IonItemSliding>
  );

  function editProductCallback(id?: string) {
    // log("editProduct");
    onEdit(id);
  }
};

export default Product;
