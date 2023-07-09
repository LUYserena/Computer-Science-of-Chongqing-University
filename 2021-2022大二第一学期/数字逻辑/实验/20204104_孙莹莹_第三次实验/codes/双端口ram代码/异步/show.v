`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date: 2021/11/21 11:27:14
// Design Name: 
// Module Name: show
// Project Name: 
// Target Devices: 
// Tool Versions: 
// Description: 
// 
// Dependencies: 
// 
// Revision:
// Revision 0.01 - File Created
// Additional Comments:
// 
//////////////////////////////////////////////////////////////////////////////////

module show(clk,dout_a,dout_b,sm_wei,sm_duan);
    input clk;
    input [1:0] dout_a;
    input [1:0] dout_b;
    output [3:0] sm_wei;
    output [7:0] sm_duan;
    
    integer clk_cnt;
reg clk_400Hz;
always@(posedge clk)
begin
    if(clk_cnt==32'd100000)
    begin
        clk_cnt<=1'b0;
        clk_400Hz<=~clk_400Hz; 
    end
    else begin
        clk_cnt<=clk_cnt+1'b1;
    end
end

//位控制
reg [3:0]wei_ctrl=4'b1110;  //共阳极
always@(posedge clk_400Hz)
begin
    wei_ctrl<={wei_ctrl[2:0],wei_ctrl[3]};
end

//段控制
reg[3:0] duan_ctrl;
always@(wei_ctrl)begin
    case(wei_ctrl)
        4'b1110:duan_ctrl=dout_a;
        4'b1101:duan_ctrl= 4'h0;
        4'b1011:duan_ctrl=dout_b;
        4'b0111:duan_ctrl= 4'h0;
        default:duan_ctrl=4'h0;
     endcase  
end

//解码模块
reg[7:0] duan;
always@(duan_ctrl)
begin
    case(duan_ctrl)
        4'h0:duan=8'b1100_0000; //h-a
        4'h1:duan=8'b1111_1001;
        4'h2:duan=8'b1010_0100;
        4'h3:duan=8'b1011_0000;
        4'h4:duan=8'b1001_1001;
        4'h5:duan=8'b1001_0010;
        4'h6:duan=8'b1000_0010;
        4'h7:duan=8'b1111_1000;
        4'h8:duan=8'b1000_0000;
        4'h9:duan=8'b1001_0000;
        4'ha:duan=8'b1000_1000;
        4'hb:duan=8'b1000_0011;
        4'hc:duan=8'b1100_0110;
        4'hd:duan=8'b1010_0001;
        4'he:duan=8'b1000_0110;
        4'hf:duan=8'b1000_1110;
        default:duan=8'b1100_0000;  
    endcase
end
    assign sm_wei = wei_ctrl;
    assign sm_duan = duan;
endmodule
